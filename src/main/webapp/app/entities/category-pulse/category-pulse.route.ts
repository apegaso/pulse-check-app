import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CategoryPulse } from 'app/shared/model/category-pulse.model';
import { CategoryPulseService } from './category-pulse.service';
import { CategoryPulseComponent } from './category-pulse.component';
import { CategoryPulseDetailComponent } from './category-pulse-detail.component';
import { CategoryPulseUpdateComponent } from './category-pulse-update.component';
import { CategoryPulseDeletePopupComponent } from './category-pulse-delete-dialog.component';
import { ICategoryPulse } from 'app/shared/model/category-pulse.model';

@Injectable({ providedIn: 'root' })
export class CategoryPulseResolve implements Resolve<ICategoryPulse> {
    constructor(private service: CategoryPulseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((category: HttpResponse<CategoryPulse>) => category.body));
        }
        return of(new CategoryPulse());
    }
}

export const categoryRoute: Routes = [
    {
        path: 'category-pulse',
        component: CategoryPulseComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Categories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'category-pulse/:id/view',
        component: CategoryPulseDetailComponent,
        resolve: {
            category: CategoryPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Categories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'category-pulse/new',
        component: CategoryPulseUpdateComponent,
        resolve: {
            category: CategoryPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Categories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'category-pulse/:id/edit',
        component: CategoryPulseUpdateComponent,
        resolve: {
            category: CategoryPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Categories'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const categoryPopupRoute: Routes = [
    {
        path: 'category-pulse/:id/delete',
        component: CategoryPulseDeletePopupComponent,
        resolve: {
            category: CategoryPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Categories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
