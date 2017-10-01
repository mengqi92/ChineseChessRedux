package self.mengqi.games.piece

import org.testng.Assert
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import self.mengqi.games.enums.PieceEnums
import self.mengqi.games.models.Coordinate
import self.mengqi.games.models.Coordinates

/**
 * Created by Mengqi on 2017/10/1.
 */
class XiangTest {
    @DataProvider(name="xiangLegs")
    Object[][] xiangLegs() {
        return new Object[][] {
            {Coordinates.of(4, 6), Coordinates.of(3, 5)},
            {Coordinates.of(4, 2), Coordinates.of(3, 3)}
        }
    }

    @Test(dataProvider = "xiangLegs")
    public testXiangLeg(Coordinate destination, Coordinate expected) {
        Xiang xiang = new Xiang(PieceEnums.Faction.Red, Coordinates.of(2, 4))
        def actual = xiang.xiangLeg(destination);
        Assert.assertEquals(actual, expected);
    }
}
