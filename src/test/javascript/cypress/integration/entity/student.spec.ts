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

describe('Student e2e test', () => {
  const studentPageUrl = '/student';
  const studentPageUrlPattern = new RegExp('/student(\\?.*)?$');
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
    cy.intercept('GET', '/api/students+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/students').as('postEntityRequest');
    cy.intercept('DELETE', '/api/students/*').as('deleteEntityRequest');
  });

  it('should load Students', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('student');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Student').should('exist');
    cy.url().should('match', studentPageUrlPattern);
  });

  it('should load details Student page', function () {
    cy.visit(studentPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        this.skip();
      }
    });
    cy.get(entityDetailsButtonSelector).first().click({ force: true });
    cy.getEntityDetailsHeading('student');
    cy.get(entityDetailsBackButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', studentPageUrlPattern);
  });

  it('should load create Student page', () => {
    cy.visit(studentPageUrl);
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Student');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.get(entityCreateCancelButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', studentPageUrlPattern);
  });

  it('should load edit Student page', function () {
    cy.visit(studentPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        this.skip();
      }
    });
    cy.get(entityEditButtonSelector).first().click({ force: true });
    cy.getEntityCreateUpdateHeading('Student');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.get(entityCreateCancelButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', studentPageUrlPattern);
  });

  it('should create an instance of Student', () => {
    cy.visit(studentPageUrl);
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Student');

    cy.get(`[data-cy="regNumber"]`).type('silver hacking').should('have.value', 'silver hacking');

    cy.get(`[data-cy="firstName"]`).type('Terrell').should('have.value', 'Terrell');

    cy.get(`[data-cy="middleName"]`).type('regional calculating').should('have.value', 'regional calculating');

    cy.get(`[data-cy="lastName"]`).type('Weber').should('have.value', 'Weber');

    cy.get(`[data-cy="dob"]`).type('2021-07-04T06:05').should('have.value', '2021-07-04T06:05');

    cy.get(`[data-cy="fatherName"]`).type('Chicken HTTP online').should('have.value', 'Chicken HTTP online');

    cy.get(`[data-cy="motherName"]`).type('Agent encoding').should('have.value', 'Agent encoding');

    cy.get(`[data-cy="email"]`).type('Rubye6@gmail.com').should('have.value', 'Rubye6@gmail.com');

    cy.get(`[data-cy="mobileNumber"]`).type('functionalities Hollow Cheese').should('have.value', 'functionalities Hollow Cheese');

    cy.get(`[data-cy="nationality"]`).type('Gibraltar exploit virtual').should('have.value', 'Gibraltar exploit virtual');

    cy.get(`[data-cy="gender"]`).select('MALE');

    cy.get(`[data-cy="religion"]`).type('portal').should('have.value', 'portal');

    cy.get(`[data-cy="catagory"]`).type('withdrawal innovative Wooden').should('have.value', 'withdrawal innovative Wooden');

    cy.get(`[data-cy="maritialStatus"]`).select('MARRIED');

    cy.get(`[data-cy="adharNumber"]`).type('Garden Iranian').should('have.value', 'Garden Iranian');

    cy.get(`[data-cy="bloodGroup"]`).select('A_POS');

    cy.get(`[data-cy="fatherMobileNumber"]`).type('invoice').should('have.value', 'invoice');

    cy.get(`[data-cy="fatherEmailId"]`).type('Hawaii Supervisor Electronics').should('have.value', 'Hawaii Supervisor Electronics');

    cy.setFieldImageAsBytesOfEntity('image', 'integration-test.png', 'image/png');

    cy.setFieldImageAsBytesOfEntity('signature', 'integration-test.png', 'image/png');

    cy.setFieldImageAsBytesOfEntity('adhar', 'integration-test.png', 'image/png');

    cy.setFieldSelectToLastOfEntity('academicBatch');

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
    cy.url().should('match', studentPageUrlPattern);
  });

  it('should delete last instance of Student', function () {
    cy.visit(studentPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', response.body.length);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.getEntityDeleteDialogHeading('student').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', studentPageUrlPattern);
      } else {
        this.skip();
      }
    });
  });
});
