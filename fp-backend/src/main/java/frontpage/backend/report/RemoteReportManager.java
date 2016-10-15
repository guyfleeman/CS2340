package frontpage.backend.report;

import frontpage.backend.rest.RESTHandler;
import frontpage.backend.rest.RESTReport;
import frontpage.bind.errorhandling.BackendRequestException;
import frontpage.bind.report.ReportManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author willstuckey
 * @date 10/14/16
 * <p></p>
 */
public class RemoteReportManager implements ReportManager {
    public String addSourceReport(final String email,
                                  final String tok)
            throws BackendRequestException {
        final Map<String, String> attribs = new HashMap<>(4);
        attribs.put("email", email);
        attribs.put("tok", tok);
        attribs.put("type", "source");
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

    public boolean updateSourceReport(final String email,
                                      final String tok,
                                      final String id,
                                      final Map<String, String> properties)
            throws BackendRequestException {
        final Map<String, String> attribs = new HashMap<>(properties);
        attribs.put("email", email);
        attribs.put("tok", tok);
        attribs.put("reprotid", id);
        attribs.put("type", "source");
        attribs.put("action", "UPDATE");
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
        return (ret.get("status") != null && ret.get("status").equals("success"));
    }

    public Map<String, String> getSourceReport(final String id)
            throws BackendRequestException {
        Map<String, String> attribs = new HashMap<>(3);
        attribs.put("type", "source");
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
        return rr.getResponseValues()[1];
    }

    public Map<String, String>[] getSourceReports(final int num)
            throws BackendRequestException {
        Map<String, String> attribs = new HashMap<>(3);
        attribs.put("type", "source");
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
        return rr.getResponseValues();
    }

    /**
     * CURRENTLY UNDER MINIMAL IMPLEMENTATION (returns null)
     * @param properties properties to search for
     * @param searchConstraints constraints for properties
     * @return
     * @throws BackendRequestException
     */
    public Map<String, String>[] getSourceReports(final Map<String, String> properties,
                                                  final Map<String, String> searchConstraints)
            throws BackendRequestException {
        return null;
    }
}
