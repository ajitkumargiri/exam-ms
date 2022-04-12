jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IExamCenter, ExamCenter } from '../exam-center.model';
import { ExamCenterService } from '../service/exam-center.service';

import { ExamCenterRoutingResolveService } from './exam-center-routing-resolve.service';

describe('Service Tests', () => {
  describe('ExamCenter routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ExamCenterRoutingResolveService;
    let service: ExamCenterService;
    let resultExamCenter: IExamCenter | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ExamCenterRoutingResolveService);
      service = TestBed.inject(ExamCenterService);
      resultExamCenter = undefined;
    });

    describe('resolve', () => {
      it('should return IExamCenter returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultExamCenter = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultExamCenter).toEqual({ id: 123 });
      });

      it('should return new IExamCenter if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultExamCenter = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultExamCenter).toEqual(new ExamCenter());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ExamCenter })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultExamCenter = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultExamCenter).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
