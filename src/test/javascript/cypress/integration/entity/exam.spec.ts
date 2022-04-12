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

describe('Exam e2e test', () => {
  const examPageUrl = '/exam';
  const examPageUrlPattern = new RegExp('/exam(\\?.*)?$');
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
    cy.intercept('GET', '/api/exams+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/exams').as('postEntityRequest');
    cy.intercept('DELETE', '/api/exams/*').as('deleteEntityRequest');
  });

  it('should load Exams', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('exam');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Exam').should('exist');
    cy.url().should('match', examPageUrlPattern);
  });

  it('should load details Exam page', function () {
    cy.visit(examPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        this.skip();
      }
    });
    cy.get(entityDetailsButtonSelector).first().click({ force: true });
    cy.getEntityDetailsHeading('exam');
    cy.get(entityDetailsBackButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', examPageUrlPattern);
  });

  it('should load create Exam page', () => {
    cy.visit(examPageUrl);
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Exam');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.get(entityCreateCancelButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', examPageUrlPattern);
  });

  it('should load edit Exam page', function () {
    cy.visit(examPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        this.skip();
      }
    });
    cy.get(entityEditButtonSelector).first().click({ force: true });
    cy.getEntityCreateUpdateHeading('Exam');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.get(entityCreateCancelButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', examPageUrlPattern);
  });

  it('should create an instance of Exam', () => {
    cy.visit(examPageUrl);
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Exam');

    cy.get(`[data-cy="name"]`).type('TCP').should('have.value', 'TCP');

    cy.get(`[data-cy="type"]`).select('REGULAR');

    cy.get(`[data-cy="code"]`).type('encoding SDD').should('have.value', 'encoding SDD');

    cy.get(`[data-cy="date"]`).type('2021-07-04T03:51').should('have.value', '2021-07-04T03:51');

    cy.get(`[data-cy="startTime"]`).type('2021-07-03').should('have.value', '2021-07-03');

    cy.get(`[data-cy="endTime"]`).type('2021-07-04').should('have.value', '2021-07-04');

    cy.setFieldSelectToLastOfEntity('correspondingAddress');

    cy.setFieldSelectToLastOfEntity('permanentAddress');

    cy.setFieldSelectToLastOfEntity('session');

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.wait('@postEntityRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(201);
    });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', examPageUrlPattern);
  });

  it('should delete last instance of Exam', function () {
    cy.visit(examPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', response.body.length);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.getEntityDeleteDialogHeading('exam').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', examPageUrlPattern);
      } else {
        this.skip();
      }
    });
  });
});
