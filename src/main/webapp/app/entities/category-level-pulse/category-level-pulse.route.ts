import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CategoryLevelPulse } from 'app/shared/model/category-level-pulse.model';
import { CategoryLevelPulseService } from './category-level-pulse.service';
import { CategoryLevelPulseComponent } from './category-level-pulse.component';
import { CategoryLevelPulseDetailComponent } from './category-level-pulse-detail.component';
import { CategoryLevelPulseUpdateComponent } from './category-level-pulse-update.component';
import { CategoryLevelPulseDeletePopupComponent } from './category-level-pulse-delete-dialog.component';
import { ICategoryLevelPulse } from 'app/shared/model/category-level-pulse.model';

@Injectable({ providedIn: 'root' })
export class CategoryLevelPulseResolve implements Resolve<ICategoryLevelPulse> {
    constructor(private service: CategoryLevelPulseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((categoryLevel: HttpResponse<CategoryLevelPulse>) => categoryLevel.body));
        }
        return of(new CategoryLevelPulse());
    }
}

export const categoryLevelRoute: Routes = [
    {
        path: 'category-level-pulse',
        component: CategoryLevelPulseComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CategoryLevels'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'category-level-pulse/:id/view',
        component: CategoryLevelPulseDetailComponent,
        resolve: {
            categoryLevel: CategoryLevelPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CategoryLevels'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'category-level-pulse/new',
        component: CategoryLevelPulseUpdateComponent,
        resolve: {
            categoryLevel: CategoryLevelPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CategoryLevels'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'category-level-pulse/:id/edit',
        component: CategoryLevelPulseUpdateComponent,
        resolve: {
            categoryLevel: CategoryLevelPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CategoryLevels'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const categoryLevelPopupRoute: Routes = [
    {
        path: 'category-level-pulse/:id/delete',
        component: CategoryLevelPulseDeletePopupComponent,
        resolve: {
            categoryLevel: CategoryLevelPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CategoryLevels'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
