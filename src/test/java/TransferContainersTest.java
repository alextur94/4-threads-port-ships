import com.epam.jwd.service.action.TransferContainers;
import org.junit.Assert;
import org.junit.Test;

public class TransferContainersTest {
    TransferContainers transferContainers = new TransferContainers();

    @Test
    public void countFreePlaceShouldBePositive(){
        int maxContainers = 20000;
        int currentContainers = 12345;
        int exFreePlace = maxContainers - currentContainers;
        int freePlace = transferContainers.countFreePlace(maxContainers, currentContainers);
        Assert.assertEquals(exFreePlace, freePlace, 1);
    }
}
