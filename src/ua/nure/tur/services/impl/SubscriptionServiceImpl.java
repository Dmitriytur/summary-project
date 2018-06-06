package ua.nure.tur.services.impl;

import ua.nure.tur.db.dao.DAOManager;
import ua.nure.tur.db.dao.DAOManagerFactory;
import ua.nure.tur.db.dao.SubscriptionDAO;
import ua.nure.tur.entities.Periodical;
import ua.nure.tur.entities.Subscription;
import ua.nure.tur.entities.User;
import ua.nure.tur.exceptions.DataAccessException;
import ua.nure.tur.exceptions.ServiceException;
import ua.nure.tur.services.ServiceResult;
import ua.nure.tur.services.ServiceResultStatus;
import ua.nure.tur.services.SubscriptionService;
import ua.nure.tur.viewmodels.UserSubscriptionViewModel;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import static ua.nure.tur.utils.ClosingUtils.close;

public class SubscriptionServiceImpl implements SubscriptionService {

    private DAOManagerFactory daoManagerFactory;

    public SubscriptionServiceImpl(DAOManagerFactory daoManagerFactory) {
        this.daoManagerFactory = daoManagerFactory;
    }

    @Override
    public List<UserSubscriptionViewModel> getUserSubscriptions(Long userId) throws ServiceException {
        DAOManager daoManager = null;

        try {
            daoManager = daoManagerFactory.getDaoManager();
            SubscriptionDAO subscriptionDAO = daoManager.getSubscriptionDao();
            List<UserSubscriptionViewModel> subscriptions = subscriptionDAO.getUserSubscriptions(userId);
            Date today = new Date(System.currentTimeMillis());
            for (UserSubscriptionViewModel model : subscriptions) {
                long periodicalId = model.getSubscription().getPeriodicalId();
                model.setActive(!today.after(model.getSubscription().getEndDate()));
                int amount = subscriptionDAO.getSubscriptionsAmountForPeriodical(periodicalId);
                model.setSubscriptionsAmount(amount);
            }
            return subscriptions;
        } catch (DataAccessException e) {
            throw new ServiceException("Cannot get user subscriptions", e);
        } finally {
            close(daoManager);
        }

    }

    @Override
    public ServiceResult<String> subscribeUserToPeriodical(Long userId, long periodicalId, int period) throws ServiceException {
        ServiceResult<String> result;
        DAOManager daoManager = null;

        try {
            daoManager = daoManagerFactory.getDaoManager();
            daoManager.startTransaction();
            User user = daoManager.getUserDao().findById(userId);
            Periodical periodical = daoManager.getPeriodicalDAO().getById(periodicalId);

            double price = periodical.getPrice() * periodical.getPeriodicity() + period;

            if (user.getBalance() >= price) {
                Calendar calendar = Calendar.getInstance();
                Date today = new Date(calendar.getTimeInMillis());
                calendar.add(Calendar.MONTH, period);
                Date endDate = new Date(calendar.getTimeInMillis());

                Subscription subscription = new Subscription();
                subscription.setUserId(user.getId());
                subscription.setPeriodicalId(periodicalId);
                subscription.setStartDate(today);
                subscription.setEndDate(endDate);

                user.setBalance(user.getBalance() - price);
                daoManager.getUserDao().update(user);

                daoManager.getSubscriptionDao().addSubscription(subscription);

                result = new ServiceResult<String>(ServiceResultStatus.SUCCESS, "You have successfully subscribed");
            } else {
                result = new ServiceResult<>(ServiceResultStatus.FAIL, "Your balance is't high enough");
            }
            daoManager.commit();
            return result;
        } catch (DataAccessException e) {
            if (daoManager != null) {
                daoManager.rollback();
            }
            throw new ServiceException("Cannot subscribe user to periodical", e);
        } finally {
            close(daoManager);
        }
    }
}
