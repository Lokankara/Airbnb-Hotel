//package com.manager.hotel.web.handler;
//
//import com.manager.hotel.exception.GuestNotFoundException;
//import com.manager.hotel.exception.NoAvailableRoomsException;
//import com.manager.hotel.exception.RoomNotFoundException;
//import jakarta.persistence.PersistenceException;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import static jakarta.servlet.RequestDispatcher.ERROR_STATUS_CODE;
//import static org.springframework.http.HttpStatus.BAD_REQUEST;
//import static org.springframework.http.HttpStatus.CONFLICT;
//import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
//import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
//import static org.springframework.http.HttpStatus.NOT_FOUND;
//
//@Slf4j
//@ControllerAdvice
//@RequestMapping("/error")
//public class ErrorHandlerController
//        extends ResponseEntityExceptionHandler
//        implements ErrorController {
//
//    @ResponseStatus(value = CONFLICT, reason = "Data integrity violation")
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public String conflict(
//            final Model model,
//            final DataIntegrityViolationException ex) {
//        model.addAttribute("status",
//                getError(CONFLICT, ex.getMessage()));
//        return "error";
//    }
//
//    @ResponseStatus(NOT_FOUND)
//    @ExceptionHandler({GuestNotFoundException.class,
//            RoomNotFoundException.class})
//    public String handleGuestNotFoundException(
//            final Model model,
//            GuestNotFoundException ex) {
//        model.addAttribute("status",
//                getError(NOT_FOUND, ex.getMessage()));
//        return "error";
//    }
//
//    @ResponseStatus(BAD_REQUEST)
//    @ExceptionHandler(NoAvailableRoomsException.class)
//    public String handleNoAvailableRoomsException(
//            final Model model,
//            NoAvailableRoomsException ex) {
//        model.addAttribute("status",
//                getError(BAD_REQUEST, ex.getMessage()));
//        return "error";
//    }
//
//    @ResponseStatus(INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(PersistenceException.class)
//    public String handlePersistenceException(
//            final Model model,
//            PersistenceException ex) {
//        log.error(ex.getMessage());
//        model.addAttribute("status",
//                getError(INTERNAL_SERVER_ERROR,
//                        "An internal error occurred."));
//        return "error";
//    }
//
//    private static ErrorResponse getError(
//            HttpStatus error, String message) {
//        return ErrorResponse.builder()
//                .status(error)
//                .message(message)
//                .build();
//    }
//
//    @ResponseStatus(METHOD_NOT_ALLOWED)
//    @ExceptionHandler(RuntimeException.class)
//    public String handleAllUncaughtException(
//            final Model model,
//            final RuntimeException ex) {
//        log.error(ex.getMessage());
//        model.addAttribute("status",
//                getError(METHOD_NOT_ALLOWED, "Method Not Allowed"));
//        return "error";
//    }
//}
