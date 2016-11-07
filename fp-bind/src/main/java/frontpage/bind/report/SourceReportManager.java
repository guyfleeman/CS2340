package frontpage.bind.report;

import frontpage.bind.errorhandling.BackendRequestException;
import java.util.Map;

/**
 * @author willstuckey
 * <p></p>
 */
public interface SourceReportManager {
    /**
     * adds a blank source report
     * @return report UUID
     */
    String addSourceReport(final String email,
                           final String tok)
            throws BackendRequestException;

    /**
     * updates a source report
     * @param id UUID of source report
     * @param properties report properties to be written
     * @return success
     */
    @SuppressWarnings("UnusedReturnValue")
    boolean updateSourceReport(final String email,
                               final String tok,
                               final String id,
                               final Map<String, String> properties)
            throws BackendRequestException;

    /**
     * gets source report from UUID
     * @param id UUID of source report
     * @return report properties
     */
    Map<String, String> getSourceReport(final String id)
            throws BackendRequestException;

    /**
     * gets most recent source reports
     * @return array containing one map describing each result
     */
    @SuppressWarnings("SameParameterValue")
    Map<String, String>[] getSourceReports(final int num)
            throws BackendRequestException;

    /**
     * gets source reports based on a set of search constraints applied to properties
     * @param properties properties to search for
     * @param searchConstraints constraints for properties
     * @return array containing one map describing each result
     */
    @SuppressWarnings("RedundantThrows")
    Map<String, String>[] getSourceReports(final Map<String, String> properties,
                                           final Map<String, String> searchConstraints)
            throws BackendRequestException;

    /**
     * deletes a source report
     * @param email
     * @param tok
     * @param id
     * @throws BackendRequestException
     */
    void deleteSourceReport(final String email,
                            final String tok,
                            final String id)
            throws BackendRequestException;

    /**
     * delete shell source reports.
     * FAIL-STOP. This method offers no indication of success an
     * as such does not guarantee it either. Used to delete shell
     * reports in failure scenarios. Shell reports missed when this
     * fails will be cleaned by server side cron after the report
     * has aged beyond a week.
     * @param email
     * @param tok
     * @param id
     */
    void __deleteSourceReport_fs_na(final String email,
                                    final String tok,
                                    final String id);
}
