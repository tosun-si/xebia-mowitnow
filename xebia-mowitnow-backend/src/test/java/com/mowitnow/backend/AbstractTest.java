package com.mowitnow.backend;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import junitparams.JUnitParamsRunner;

/**
 * Abstract superclass of integrations tests.
 * 
 * @author Mazlum TOSUN
 */
@RunWith(JUnitParamsRunner.class)
@SpringBootTest
public abstract class AbstractTest {

  @ClassRule
  public static final SpringClassRule SCR = new SpringClassRule();

  @Rule
  public final SpringMethodRule springMethodRule = new SpringMethodRule();
}
