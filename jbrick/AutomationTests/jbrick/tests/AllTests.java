package jbrick.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ContainerFS.class, FileFS.class, MenuFS.class })
public class AllTests {

}
