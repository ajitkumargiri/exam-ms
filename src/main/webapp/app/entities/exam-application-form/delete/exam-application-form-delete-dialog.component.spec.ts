jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ExamApplicationFormService } from '../service/exam-application-form.service';

import { ExamApplicationFormDeleteDialogComponent } from './exam-application-form-delete-dialog.component';

describe('Component Tests', () => {
  describe('ExamApplicationForm Management Delete Component', () => {
    let comp: ExamApplicationFormDeleteDialogComponent;
    let fixture: ComponentFixture<ExamApplicationFormDeleteDialogComponent>;
    let service: ExamApplicationFormService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ExamApplicationFormDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(ExamApplicationFormDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExamApplicationFormDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ExamApplicationFormService);
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({})));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        jest.spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.close).not.toHaveBeenCalled();
        expect(mockActiveModal.dismiss).toHaveBeenCalled();
      });
    });
  });
});
