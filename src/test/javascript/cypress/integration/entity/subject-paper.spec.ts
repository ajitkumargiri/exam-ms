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

describe('SubjectPaper e2e test', () => {
  const subjectPaperPageUrl = '/subject-paper';
  const subjectPaperPageUrlPattern = new RegExp('/subject-paper(\\?.*)?$');
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
    cy.intercept('GET', '/api/subject-papers+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/subject-papers').as('postEntityRequest');
    cy.intercept('DELETE', '/api/subject-papers/*').as('deleteEntityRequest');
  });

  it('should load SubjectPapers', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('subject-paper');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SubjectPaper').should('exist');
    cy.url().should('match', subjectPaperPageUrlPattern);
  });

  it('should load details SubjectPaper page', function () {
    cy.visit(subjectPaperPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        this.skip();
      }
    });
    cy.get(entityDetailsButtonSelector).first().click({ force: true });
    cy.getEntityDetailsHeading('subjectPaper');
    cy.get(entityDetailsBackButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', subjectPaperPageUrlPattern);
  });

  it('should load create SubjectPaper page', () => {
    cy.visit(subjectPaperPageUrl);
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('SubjectPaper');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.get(entityCreateCancelButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', subjectPaperPageUrlPattern);
  });

  it('should load edit SubjectPaper page', function () {
    cy.visit(subjectPaperPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        this.skip();
      }
    });
    cy.get(entityEditButtonSelector).first().click({ force: true });
    cy.getEntityCreateUpdateHeading('SubjectPaper');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.get(entityCreateCancelButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', subjectPaperPageUrlPattern);
  });

  it('should create an instance of SubjectPaper', () => {
    cy.visit(subjectPaperPageUrl);
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('SubjectPaper');

    cy.get(`[data-cy="name"]`).type('Tala').should('have.value', 'Tala');

    cy.get(`[data-cy="code"]`).type('Fall cross-platform').should('have.value', 'Fall cross-platform');

    cy.get(`[data-cy="type"]`).select('PRACTICAL');

    cy.get(`[data-cy="fullMark"]`).type('41507').should('have.value', '41507');

    cy.get(`[data-cy="passMark"]`).type('55905').should('have.value', '55905');

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
    cy.url().should('match', subjectPaperPageUrlPattern);
  });

  it('should delete last instance of SubjectPaper', function () {
    cy.visit(subjectPaperPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', response.body.length);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.getEntityDeleteDialogHeading('subjectPaper').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', subjectPaperPageUrlPattern);
      } else {
        this.skip();
      }
    });
  });
});
