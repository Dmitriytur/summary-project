package ua.nure.tur.web.controllers;


import ua.nure.tur.exceptions.ServiceException;
import ua.nure.tur.services.ReviewService;
import ua.nure.tur.services.ServiceFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/page/admin/reviews")
public class AdminReviewController extends HttpServlet {


    private ReviewService reviewService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        reviewService = ServiceFactory.getFactory().getReviewService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        switch (action) {
            case "delete": {
                String reviewIdParam = req.getParameter("reviewId");
                String periodicalIdParam = req.getParameter("periodicalId");
                try {
                    long reviewId = Long.parseLong(reviewIdParam);
                    long periodicalId = Long.parseLong(periodicalIdParam);
                    try {
                        reviewService.remove(reviewId);
                        resp.sendRedirect("/page/magazine?item=" + periodicalId);
                    } catch (ServiceException e) {
                        resp.setStatus(500);
                    }
                } catch (NumberFormatException e) {
                    resp.setStatus(400);
                    return;
                }
                break;
            }
        }
    }
}
