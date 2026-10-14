package com.example.pocagentjava.archunittests;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.core.domain.JavaClasses;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class HexagonalArchitectureTest {

  private static JavaClasses classes;

  @BeforeAll
  static void importProductionClasses() {
    classes = new ClassFileImporter()
        .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
        .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_ARCHIVES)
        .importPackages("com.example.pocagentjava");
    assertTrue(classes.size() > 0, "ArchUnit must import production classes");
  }

  @Test
  void domainDoesNotDependOnFrameworksOrOuterLayers() {
    noClasses().that().resideInAPackage("..domain..")
        .should().dependOnClassesThat().resideInAnyPackage(
            "org.springframework..",
            "jakarta.persistence..",
            "jakarta.servlet..",
            "org.springframework.http..",
            "..application..",
            "..infrastructure..")
        .check(classes);
  }

  @Test
  void applicationDoesNotDependOnInfrastructureOrTransport() {
    noClasses().that().resideInAPackage("..application..")
        .should().dependOnClassesThat().resideInAnyPackage(
            "..infrastructure..",
            "org.springframework.web..",
            "org.springframework.data..",
            "org.springframework.jdbc..",
            "org.springframework.http..",
            "jakarta.persistence..",
            "jakarta.servlet..")
        .check(classes);
  }

  @Test
  void inboundRestDoesNotAccessPersistenceAdaptersOrRepositories() {
    noClasses().that().resideInAPackage("..infrastructure.adapters.inbound..")
        .should().dependOnClassesThat().resideInAnyPackage(
            "..infrastructure.adapters.outbound..",
            "..infrastructure.adapters.outbound.persistence.repository..",
            "org.springframework.data..")
        .check(classes);
  }

  @Test
  void projectDoesNotUseFieldInjectionOrCyclicSlices() {
    NO_CLASSES_SHOULD_USE_FIELD_INJECTION.check(classes);
    slices().matching("com.example.pocagentjava.(*)..")
        .should().beFreeOfCycles()
        .check(classes);
  }
}
