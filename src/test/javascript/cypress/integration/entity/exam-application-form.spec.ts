import { entityItemSelector } from '../../support/commands';
import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('ExamApplicationForm e2e test', () => {
  const examApplicationFormPageUrl = '/exam-application-form';
  const examApplicationFormPageUrlPattern = new RegExp('/exam-application-form(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'admin';
  const password = Cypress.env('E2E_PASSWORD') ?? 'admin';

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });
    cy.visit('');
    cy.login(username, password);
    cy.get(entityItemSelector).should('exist');
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/exam-application-forms+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/exam-application-forms').as('postEntityRequest');
    cy.intercept('DELETE', '/api/exam-application-forms/*').as('deleteEntityRequest');
  });

  it('should load ExamApplicationForms', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('exam-application-form');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ExamApplicationForm').should('exist');
    cy.url().should('match', examApplicationFormPageUrlPattern);
  });

  it('should load details ExamApplicationForm page', function () {
    cy.visit(examApplicationFormPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        this.skip();
      }
    });
    cy.get(entityDetailsButtonSelector).first().click({ force: true });
    cy.getEntityDetailsHeading('examApplicationForm');
    cy.get(entityDetailsBackButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', examApplicationFormPageUrlPattern);
  });

  it('should load create ExamApplicationForm page', () => {
    cy.visit(examApplicationFormPageUrl);
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('ExamApplicationForm');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.get(entityCreateCancelButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', examApplicationFormPageUrlPattern);
  });

  it('should load edit ExamApplicationForm page', function () {
    cy.visit(examApplicationFormPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        this.skip();
      }
    });
    cy.get(entityEditButtonSelector).first().click({ force: true });
    cy.getEntityCreateUpdateHeading('ExamApplicationForm');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.get(entityCreateCancelButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', examApplicationFormPageUrlPattern);
  });

  it('should create an instance of ExamApplicationForm', () => {
    cy.visit(examApplicationFormPageUrl);
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('ExamApplicationForm');

    cy.get(`[data-cy="regNumber"]`).type('Chicken revolutionize Investment').should('have.value', 'Chicken revolutionize Investment');

    cy.get(`[data-cy="firstName"]`).type('Gracie').should('have.value', 'Gracie');

    cy.get(`[data-cy="middleName"]`).type('Associate Arkansas').should('have.value', 'Associate Arkansas');

    cy.get(`[data-cy="lastName"]`).type('Aufderhar').should('have.value', 'Aufderhar');

    cy.get(`[data-cy="dob"]`).type('2021-07-03T19:12').should('have.value', '2021-07-03T19:12');

    cy.get(`[data-cy="fatherName"]`).type('Administrator portal Avon').should('have.value', 'Administrator portal Avon');

    cy.get(`[data-cy="email"]`).type('Marjory.Jast@hotmail.com').should('have.value', 'Marjory.Jast@hotmail.com');

    cy.get(`[data-cy="mobileNumber"]`).type('Car Stream Auto').should('have.value', 'Car Stream Auto');

    cy.get(`[data-cy="nationality"]`).type('navigate out-of-the-box Chief').should('have.value', 'navigate out-of-the-box Chief');

    cy.get(`[data-cy="gender"]`).select('OTHER');

    cy.get(`[data-cy="religion"]`).type('Gloves Reduced').should('have.value', 'Gloves Reduced');

    cy.get(`[data-cy="adharNumber"]`).type('Cambridgeshire back orchid').should('have.value', 'Cambridgeshire back orchid');

    cy.get(`[data-cy="bloodGroup"]`).select('A_POS');

    cy.get(`[data-cy="isApproved"]`).should('not.be.checked');
    cy.get(`[data-cy="isApproved"]`).click().should('be.checked');

    cy.get(`[data-cy="catagory"]`).type('enterprise Gloves').should('have.value', 'enterprise Gloves');

    cy.get(`[data-cy="identityType"]`).select('ADHAR');

    cy.get(`[data-cy="identityNumber"]`).type('protocol').should('have.value', 'protocol');

    cy.setFieldImageAsBytesOfEntity('image', 'integration-test.png', 'image/png');

    cy.setFieldImageAsBytesOfEntity('signature', 'integration-test.png', 'image/png');

    cy.setFieldImageAsBytesOfEntity('adhar', 'integration-test.png', 'image/png');

    cy.get(`[data-cy="imagePath"]`).type('Pound').should('have.value', 'Pound');

    cy.get(`[data-cy="signPath"]`).type('Intelligent').should('have.value', 'Intelligent');

    cy.get(`[data-cy="adharPath"]`).type('Sudan Account').should('have.value', 'Sudan Account');

    cy.setFieldSelectToLastOfEntity('correspondingAddress');

    cy.setFieldSelectToLastOfEntity('student');

    cy.setFieldSelectToLastOfEntity('exam');

    cy.setFieldSelectToLastOfEntity('examCenter');

    // since cypress clicks submit too fast before the blob fields are validated
    cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.wait('@postEntityRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(201);
    });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', examApplicationFormPageUrlPattern);
  });

  it('should delete last instance of ExamApplicationForm', function () {
    cy.visit(examApplicationFormPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', response.body.length);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.getEntityDeleteDialogHeading('examApplicationForm').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', examApplicationFormPageUrlPattern);
      } else {
        this.skip();
      }
    });
  });
});
