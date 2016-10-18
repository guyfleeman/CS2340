package frontpage.backend.report;

import frontpage.bind.errorhandling.BackendRequestException;
import frontpage.bind.report.ReportManager;

import java.util.Map;

/**
 * @author willstuckey
 * @date 10/14/16
 * <p></p>
 */
public class LocalReportManager implements ReportManager {
    @Override
    public String addSourceReport(final String email,
                                  final String tok)
            throws BackendRequestException {
        return null;
    }

    @Override
    public boolean updateSourceReport(final String email,
                                      final String tok,
                                      final String id,
                                      final Map<String, String> properties)
            throws BackendRequestException {
        return false;
    }

    @Override
    public Map<String, String> getSourceReport(final String id)
            throws BackendRequestException {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, String>[] getSourceReports(final int num)
            throws BackendRequestException {
        return (Map<String, String>[]) new Map[0];
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, String>[] getSourceReports(final Map<String, String> properties,
                                                  final Map<String, String> searchConstraints)
            throws BackendRequestException {
        return (Map<String, String>[]) new Map[0];
    }

    @Override
    public void deleteSourceReport(String email, String tok, String id)
            throws BackendRequestException {

    }

    @Override
    public void __deleteSourceReport_fs_na(String email, String tok, String id) {
        return;
    }
}
