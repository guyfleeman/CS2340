package frontpage.bind.report;

import frontpage.bind.errorhandling.BackendRequestException;

import java.util.Map;

/**
 * @author willstuckey
 * <p></p>
 */
@SuppressWarnings("unused")
public interface PurityReportManager {
    /**
     * adds a blank purity report
     * @param email email for auth
     * @param tok token for auth
     * @return report UUID
     * @throws BackendRequestException if things go wrong
     */
    String addPurityReport(final String email,
                           final String tok)
            throws BackendRequestException;

    /**
     * updates a purity report
     * @param email email for auth
     * @param tok token for auth
     * @param id UUID of source report
     * @param properties report properties to be written
     * @return success
     * @throws BackendRequestException if things go wrong
     */
    @SuppressWarnings("UnusedReturnValue")
    boolean updatePurityReport(final String email,
                               final String tok,
                               final String id,
                               final Map<String, String> properties)
            throws BackendRequestException;

    /**
     * gets purity report from UUID
     * @param id UUID of source report
     * @return report properties
     * @throws BackendRequestException if things go wrong
     */
    Map<String, String> getPurityReport(final String id)
            throws BackendRequestException;

    /**
     * gets most recent purity reports
     * @param num report history length
     * @return array containing one map describing each result
     * @throws BackendRequestException if things go wrong
     */
    @SuppressWarnings({"SameParameterValue", "UnusedParameters"})
    Map<String, String>[] getPurityReports(final int num)
            throws BackendRequestException;

    /**
     * gets purity reports based on a set of search constraints
     * applied to properties
     * @param properties properties to search for
     * @param searchConstraints constraints for properties
     * @return array containing one map describing each result
     * @throws BackendRequestException if things go wrong
     */
    @SuppressWarnings({"RedundantThrows", "UnusedParameters"})
    Map<String, String>[] getPurityReports(
            final Map<String, String> properties,
            final Map<String, String> searchConstraints)
            throws BackendRequestException;

    /**
     * deletes a purity report
     * @param email email for auth
     * @param tok token for auth
     * @param id report id
     * @throws BackendRequestException errors
     */
    void deletePurityReport(final String email,
                            final String tok,
                            final String id)
            throws BackendRequestException;

    /**
     * delete shell purity reports.
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
    void __deletePurityReport_fs_na(final String email,
                                    final String tok,
                                    final String id);
    //CHECKSTYLE.ON: MethodName
}
