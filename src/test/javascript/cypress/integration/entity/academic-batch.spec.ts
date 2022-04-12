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

describe('AcademicBatch e2e test', () => {
  const academicBatchPageUrl = '/academic-batch';
  const academicBatchPageUrlPattern = new RegExp('/academic-batch(\\?.*)?$');
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
    cy.intercept('GET', '/api/academic-batches+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/academic-batches').as('postEntityRequest');
    cy.intercept('DELETE', '/api/academic-batches/*').as('deleteEntityRequest');
  });

  it('should load AcademicBatches', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('academic-batch');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('AcademicBatch').should('exist');
    cy.url().should('match', academicBatchPageUrlPattern);
  });

  it('should load details AcademicBatch page', function () {
    cy.visit(academicBatchPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        this.skip();
      }
    });
    cy.get(entityDetailsButtonSelector).first().click({ force: true });
    cy.getEntityDetailsHeading('academicBatch');
    cy.get(entityDetailsBackButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', academicBatchPageUrlPattern);
  });

  it('should load create AcademicBatch page', () => {
    cy.visit(academicBatchPageUrl);
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('AcademicBatch');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.get(entityCreateCancelButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', academicBatchPageUrlPattern);
  });

  it('should load edit AcademicBatch page', function () {
    cy.visit(academicBatchPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        this.skip();
      }
    });
    cy.get(entityEditButtonSelector).first().click({ force: true });
    cy.getEntityCreateUpdateHeading('AcademicBatch');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.get(entityCreateCancelButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', academicBatchPageUrlPattern);
  });

  it('should create an instance of AcademicBatch', () => {
    cy.visit(academicBatchPageUrl);
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('AcademicBatch');

    cy.get(`[data-cy="name"]`).type('circuit').should('have.value', 'circuit');

    cy.get(`[data-cy="code"]`).type('RSS wireless magenta').should('have.value', 'RSS wireless magenta');

    cy.get(`[data-cy="academicStartYear"]`).type('25164').should('have.value', '25164');

    cy.get(`[data-cy="academicEndYear"]`).type('13520').should('have.value', '13520');

    cy.setFieldSelectToLastOfEntity('branch');

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.wait('@postEntityRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(201);
    });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', academicBatchPageUrlPattern);
  });

  it('should delete last instance of AcademicBatch', function () {
    cy.visit(academicBatchPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', response.body.length);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.getEntityDeleteDialogHeading('academicBatch').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', academicBatchPageUrlPattern);
      } else {
        this.skip();
      }
    });
  });
});
