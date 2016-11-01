package frontpage.backend.report;

import frontpage.bind.errorhandling.BackendRequestException;
import frontpage.bind.report.PurityReportManager;

import java.util.Map;

/**
 * @author willstuckey
 * @date 10/31/16
 * <p></p>
 */
public class LocalPurityReportManager implements PurityReportManager {
    @Override
    public String addPurityReport(final String email, final String tok)
            throws BackendRequestException {
        return null;
    }

    @Override
    public boolean updatePurityReport(final String email,
                                      final String tok,
                                      final String id,
                                      final Map<String, String> properties)
            throws BackendRequestException {
        return false;
    }

    @Override
    public Map<String, String> getPurityReport(final String id)
            throws BackendRequestException {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, String>[] getPurityReports(final int num)
            throws BackendRequestException {
        return (Map<String, String>[]) new Map[0];
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, String>[] getPurityReports(
            final Map<String, String> properties,
            final Map<String, String> searchConstraints)
            throws BackendRequestException {
        return (Map<String, String>[]) new Map[0];
    }

    @Override
    public void deletePurityReport(final String email,
                                   final String tok,
                                   final String id)
            throws BackendRequestException {

    }

    @Override
    public void __deletePurityReport_fs_na(final String email,
                                           final String tok,
                                           final String id) {

    }
}
