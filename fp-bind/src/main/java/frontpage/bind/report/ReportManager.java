package frontpage.bind.report;

import frontpage.bind.errorhandling.BackendRequestException;
import java.util.Map;

/**
 * @author willstuckey
 * @date 10/14/16
 * <p></p>
 */
public interface ReportManager {
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
    Map<String, String>[] getSourceReports(final int num)
            throws BackendRequestException;

    /**
     * gets source reports based on a set of search constraints applied to properties
     * @param properties properties to search for
     * @param searchConstraints constraints for properties
     * @return array containing one map describing each result
     */
    Map<String, String>[] getSourceReports(final Map<String, String> properties,
                                           final Map<String, String> searchConstraints)
            throws BackendRequestException;
}
