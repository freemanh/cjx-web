package com.chejixing.integrationtest;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chejixing.config.WhTempConfig;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WhTempConfig.class)
@ActiveProfiles(value = "test")
public class BaseSpringIT extends AbstractTransactionalJUnit4SpringContextTests {

}
