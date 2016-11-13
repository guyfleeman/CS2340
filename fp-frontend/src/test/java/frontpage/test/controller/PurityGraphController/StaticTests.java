package frontpage.test.controller.PurityGraphController;


import frontpage.controller.PurityGraphController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;

/**
 * @author willstuckey
 * @date 11/12/16
 * <p></p>
 */
public class StaticTests {

    /**
     * test the avgListByKey
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testAvgListByKeyNullNullContract() {
        Method avgListByKey = null;
        try {
            avgListByKey = PurityGraphController.class.getDeclaredMethod("avgListByKey", ObservableList.class);
            avgListByKey.setAccessible(true);
        } catch (Exception e) {
            Assert.fail();
        }

        try {
            ObservableList<String> list = null;
            ObservableList<String> ret = (ObservableList<String>) avgListByKey.invoke(null, list);
            Assert.assertNull(ret);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testAvgListByKeyTypeError() {
        Method avgListByKey = null;
        try {
            avgListByKey = PurityGraphController.class.getDeclaredMethod("avgListByKey", ObservableList.class);
            avgListByKey.setAccessible(true);
        } catch (Exception e) {
            Assert.fail();
        }

        try {
            ObservableList<String> list = FXCollections.observableList(new ArrayList<String>() {
                {
                    add("data1");
                }
            });
            ObservableList<String> ret = (ObservableList<String>) avgListByKey.invoke(null, list);
        } catch (Exception e) {
            //should be type error
            Assert.assertTrue(true);
            return;
        }

        //no type error
        Assert.fail();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testAvgListByKeySingleKeys() {
        Method avgListByKey = null;
        try {
            avgListByKey = PurityGraphController.class.getDeclaredMethod("avgListByKey", ObservableList.class);
            avgListByKey.setAccessible(true);
        } catch (Exception e) {
            Assert.fail();
        }

        try {
            ObservableList<XYChart.Data<String, Integer>> data = FXCollections.observableArrayList();
            data.addAll(new XYChart.Data<>("data1", 100));
            data.addAll(new XYChart.Data<>("data2", 50));

            ObservableList<XYChart.Data<String, Integer>> result = FXCollections.observableArrayList();
            result.addAll(new XYChart.Data<>("data1", 100));
            result.addAll(new XYChart.Data<>("data2", 50));

            ObservableList<XYChart.Data<String, Integer>> ret =
                    (ObservableList<XYChart.Data<String, Integer>>)
                            avgListByKey.invoke(null, data);

            Assert.assertEquals(result.size(), ret.size());
            for (int i = 0; i < ret.size(); i++) {
                XYChart.Data<String, Integer> retEnt = ret.get(i);
                XYChart.Data<String, Integer> resEnt = result.get(i);

                Assert.assertEquals(retEnt.getXValue(), resEnt.getXValue());
                Assert.assertEquals(retEnt.getYValue(), resEnt.getYValue());
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            Assert.fail("exception: " + e.getTargetException());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("exception: " + e.getCause());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testAvgListByKeyMultipleKeys() {
        Method avgListByKey = null;
        try {
            avgListByKey = PurityGraphController.class.getDeclaredMethod("avgListByKey", ObservableList.class);
            avgListByKey.setAccessible(true);
        } catch (Exception e) {
            Assert.fail();
        }

        try {
            ObservableList<XYChart.Data<String, Integer>> data = FXCollections.observableArrayList();
            data.addAll(new XYChart.Data<>("data1", 100));
            data.addAll(new XYChart.Data<>("data1", 0));
            data.addAll(new XYChart.Data<>("data2", 50));
            data.addAll(new XYChart.Data<>("data2", 100));

            ObservableList<XYChart.Data<String, Integer>> result = FXCollections.observableArrayList();
            result.addAll(new XYChart.Data<>("data1", 50));
            result.addAll(new XYChart.Data<>("data2", 75));

            ObservableList<XYChart.Data<String, Integer>> ret =
                    (ObservableList<XYChart.Data<String, Integer>>)
                            avgListByKey.invoke(null, data);

            Assert.assertEquals(result.size(), ret.size());
            for (int i = 0; i < ret.size(); i++) {
                XYChart.Data<String, Integer> retEnt = ret.get(i);
                XYChart.Data<String, Integer> resEnt = result.get(i);

                Assert.assertEquals(retEnt.getXValue(), resEnt.getXValue());
                Assert.assertEquals(retEnt.getYValue(), resEnt.getYValue());
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            Assert.fail("exception: " + e.getTargetException());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("exception: " + e.getCause());
        }
    }
}
