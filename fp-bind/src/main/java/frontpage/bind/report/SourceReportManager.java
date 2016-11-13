package frontpage.bind.report;

import frontpage.bind.errorhandling.BackendRequestException;
import java.util.Map;

/**
 * @author willstuckey
 * <p>Source Report Manager</p>
 */
@SuppressWarnings("unused")
public interface SourceReportManager {
    /**
     * adds a blank source report
     * @param email email for auth
     * @param tok token for auth
     * @return report UUID
     * @throws BackendRequestException if anything goes wrong
     */
    String addSourceReport(final String email,
                           final String tok)
            throws BackendRequestException;

    /**
     * updates a source report
     * @param email email for auth
     * @param tok token for auth
     * @param id report if
     * @param properties properties map
     * @return success
     * @throws BackendRequestException if anything goes wrong
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
     * @throws BackendRequestException if anything goes wrong
     */
    Map<String, String> getSourceReport(final String id)
            throws BackendRequestException;

    /**
     * gets most recent source reports
     * @param num number of reports
     * @return array containing one map describing each result
     * @throws BackendRequestException if anything goes wrong
     */
    @SuppressWarnings({"SameParameterValue", "UnusedParameters"})
    Map<String, String>[] getSourceReports(final int num)
            throws BackendRequestException;

    /**
     * gets source reports based on a set of search constraints
     * applied to properties
     * @param properties properties to search for
     * @param searchConstraints constraints for properties
     * @return array containing one map describing each result
     * @throws BackendRequestException if anything goes wrong
     */
    @SuppressWarnings({"RedundantThrows", "UnusedParameters"})
    Map<String, String>[] getSourceReports(
            final Map<String, String> properties,
            final Map<String, String> searchConstraints)
            throws BackendRequestException;

    /**
     * deletes a source report
     * @param email email for auth
     * @param tok token for auth
     * @param id report id
     * @throws BackendRequestException errors
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
     * @param email email for auth
     * @param tok token for auth
     * @param id report id
     */
    //CHECKSTYLE.OFF: MethodName
    void __deleteSourceReport_fs_na(final String email,
                                    final String tok,
                                    final String id);
    //CHECKSTYLE.ON: MethodName
}
