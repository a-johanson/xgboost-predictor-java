package biz.k11i.xgboost;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(Theories.class)
public class PredictionUsingSparkModelTest extends PredictionTestBase {

    static class TestDataSet {
        final String name;
        final String testDataName;

        TestDataSet(String name, String testDataName) {
            this.name = name;
            this.testDataName = testDataName;
        }

        PredictionModel predictionModel() {
            return new PredictionModel("model/gbtree/spark/" + name + ".model.spark");
        }

        TestData testData() {
            return TestData.oneBasedIndex("data/" + testDataName);
        }

        ExpectedData expectedData() {
            return new ExpectedData("expected/gbtree/spark/" + name + ".predict");
        }
    }

    @DataPoints
    public static final TestDataSet[] dataPoints = {
            new TestDataSet("agaricus", "agaricus.txt.test.1"),
            new TestDataSet("iris", "iris.test"),
            new TestDataSet("housing", "housing.test"),
    };

    @Theory
    public void test(TestDataSet testDataSet) throws IOException {
        verify(
                testDataSet.predictionModel(),
                testDataSet.testData(),
                testDataSet.expectedData(),
                PredictionTask.predict());
    }
}
