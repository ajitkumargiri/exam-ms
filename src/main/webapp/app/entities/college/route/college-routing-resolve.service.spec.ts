jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICollege, College } from '../college.model';
import { CollegeService } from '../service/college.service';

import { CollegeRoutingResolveService } from './college-routing-resolve.service';

describe('Service Tests', () => {
  describe('College routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CollegeRoutingResolveService;
    let service: CollegeService;
    let resultCollege: ICollege | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CollegeRoutingResolveService);
      service = TestBed.inject(CollegeService);
      resultCollege = undefined;
    });

    describe('resolve', () => {
      it('should return ICollege returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCollege = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCollege).toEqual({ id: 123 });
      });

      it('should return new ICollege if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCollege = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCollege).toEqual(new College());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as College })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCollege = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCollege).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
