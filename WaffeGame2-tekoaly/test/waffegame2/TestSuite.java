package waffegame2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Walter
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    waffegame2.card.CardTests.class,
    waffegame2.player.ai.MinimaxTreeWaffeGame2Tests.class,
    waffegame2.player.ai.MinimaxNodeTests.class,
    waffegame2.util.UtilTests.class,
    waffegame2.util.WaffeListTests.class,
    waffegame2.util.WaffeMapTests.class,
    waffegame2.util.WaffeSetTests.class
})
public class TestSuite {

}
