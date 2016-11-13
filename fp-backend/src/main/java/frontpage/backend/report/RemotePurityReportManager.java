package frontpage.backend.report;

import frontpage.backend.rest.RESTHandler;
import frontpage.backend.rest.RESTReport;
import frontpage.bind.errorhandling.BackendRequestException;
import frontpage.bind.report.PurityReportManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author willstuckey
 * <p>Purity Report Manager for remote server</p>
 */
public class RemotePurityReportManager implements PurityReportManager {
    /**
     * adds a purity report
     * @param email email for auth
     * @param tok token for auth
     * @return report id
     * @throws BackendRequestException errors
     */
    public String addPurityReport(final String email,
                                  final String tok)
            throws BackendRequestException {
        final Map<String, String> attribs = new HashMap<>(4);
        attribs.put("email", email);
        attribs.put("tok", tok);
        attribs.put("reporttype", "purity");
        attribs.put("action", "ADD");
        RESTReport rr = RESTHandler.apiRequest(RESTHandler.RestAction.POST,
                RESTHandler.REPORT_ENTRY_POINT,
                attribs);
        if (rr.rejected()) {
            throw new BackendRequestException(rr.toString());
        }

        if (!rr.success()) {
            throw new BackendRequestException(rr.getResponseValue("message"));
        }

        Map<String, String> ret = new HashMap<>(rr.getSingleResponseMap());
        String id = ret.get("reportid");
        if (id == null || id.length() == 0) {
            throw new BackendRequestException("invalid report id");
        }
        return id;
    }

    /**
     * updates a purity report with new data
     * @param email email for auth
     * @param tok token for auth
     * @param id UUID of source report
     * @param properties report properties to be written
     * @return success
     * @throws BackendRequestException errors
     */
    public boolean updatePurityReport(final String email,
                                      final String tok,
                                      final String id,
                                      final Map<String, String> properties)
            throws BackendRequestException {
        final Map<String, String> attribs = new HashMap<>(properties);
        attribs.put("email", email);
        attribs.put("tok", tok);
        attribs.put("reportid", id);
        attribs.put("reporttype", "purity");
        attribs.put("action", "UPDATE");
        attribs.keySet().stream().filter(k -> attribs.get(k) == null
                || attribs.get(k).length() == 0)
                .forEach(k -> attribs.put(k, "NULL"));
        RESTReport rr = RESTHandler.apiRequest(RESTHandler.RestAction.POST,
                RESTHandler.REPORT_ENTRY_POINT,
                attribs);
        if (rr.rejected()) {
            throw new BackendRequestException(rr.toString());
        }

        if (!rr.success()) {
            throw new BackendRequestException(rr.getResponseValue("message"));
        }

        Map<String, String> ret = rr.getSingleResponseMap();
        return (ret.get("status") != null
                && ret.get("status").equals("success"));
    }

    /**
     * gets a purity report
     * @param id UUID of source report
     * @return report data
     * @throws BackendRequestException errors
     */
    public Map<String, String> getPurityReport(final String id)
            throws BackendRequestException {
        Map<String, String> attribs = new HashMap<>();
        attribs.put("reporttype", "purity");
        attribs.put("action", "GET");
        attribs.put("reportid", id);
        RESTReport rr = RESTHandler.apiRequest(
                RESTHandler.RestAction.POST,
                RESTHandler.REPORT_ENTRY_POINT,
                attribs);
        if (rr.rejected()) {
            throw new BackendRequestException(rr.toString());
        }

        if (!rr.success()) {
            throw new BackendRequestException(rr.getResponseValue("message"));
        }

        Map<String, String> ret = rr.getResponseValues()[1];
        ret.keySet().stream().filter(k -> ret.get(k) == null
                || ret.get(k).length() == 0
                || ret.get(k).equalsIgnoreCase("null"))
                .forEach(k -> ret.put(k, ""));
        return rr.getResponseValues()[1];
    }

    /**
     * gets a number of purity reports
     * @param num number of reports in history
     * @return report data
     * @throws BackendRequestException error
     */
    public Map<String, String>[] getPurityReports(final int num)
            throws BackendRequestException {
        Map<String, String> attribs = new HashMap<>();
        attribs.put("reporttype", "purity");
        attribs.put("action", "GET");
        attribs.put("reportid", "ALL");
        RESTReport rr = RESTHandler.apiRequest(
                RESTHandler.RestAction.POST,
                RESTHandler.REPORT_ENTRY_POINT,
                attribs);
        if (rr.rejected()) {
            throw new BackendRequestException(rr.toString());
        }

        if (!rr.success()) {
            throw new BackendRequestException(rr.getResponseValue("message"));
        }

        Map<String, String>[] ret = rr.getResponseValues();
        for (Map<String, String> m : ret) {
            m.keySet().stream().filter(k -> m.get(k) == null
                    || m.get(k).length() == 0
                    || m.get(k).equalsIgnoreCase("null"))
                    .forEach(k -> m.put(k, ""));
        }
        return rr.getResponseValues();
    }

    /**
     * CURRENTLY UNDER MINIMAL IMPLEMENTATION (returns null)
     * @param properties properties to search for
     * @param searchConstraints constraints for properties
     * @return null
     * @throws BackendRequestException errors
     */
    public Map<String, String>[] getPurityReports(
            final Map<String, String> properties,
            final Map<String, String> searchConstraints)
            throws BackendRequestException {
        return null;
    }

    /**
     * deletes a purity report
     * @param email email for auth
     * @param tok token for auth
     * @param id report id
     * @throws BackendRequestException errors
     */
    public void deletePurityReport(final String email,
                                   final String tok,
                                   final String id)
            throws BackendRequestException {
        final Map<String, String> attribs = new HashMap<>(5);
        attribs.put("email", email);
        attribs.put("tok", tok);
        attribs.put("reportid", id);
        attribs.put("reporttype", "purity");
        attribs.put("action", "DELETE");
        RESTReport rr = RESTHandler.apiRequest(
                RESTHandler.RestAction.POST,
                RESTHandler.REPORT_ENTRY_POINT,
                attribs);
        if (rr.rejected()) {
            throw new BackendRequestException(rr.toString());
        }

        if (!rr.success()) {
            throw new BackendRequestException(rr.getResponseValue("message"));
        }
    }

    /**
     * deletes a purity report
     *
     * stop on error rather than propagating it up
     * @param email email for auth
     * @param tok token for auth
     * @param id report id
     */
    @Override
    public void __deletePurityReport_fs_na(final String email,
                                           final String tok,
                                           final String id) {
        //noinspection EmptyCatchBlock
        try {
            deletePurityReport(email, tok, id);
        } catch (Exception e) { }
    }
}
