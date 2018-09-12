import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { UserExtPulse } from 'app/shared/model/user-ext-pulse.model';
import { UserExtPulseService } from './user-ext-pulse.service';
import { UserExtPulseComponent } from './user-ext-pulse.component';
import { UserExtPulseDetailComponent } from './user-ext-pulse-detail.component';
import { UserExtPulseUpdateComponent } from './user-ext-pulse-update.component';
import { UserExtPulseDeletePopupComponent } from './user-ext-pulse-delete-dialog.component';
import { IUserExtPulse } from 'app/shared/model/user-ext-pulse.model';

@Injectable({ providedIn: 'root' })
export class UserExtPulseResolve implements Resolve<IUserExtPulse> {
    constructor(private service: UserExtPulseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((userExt: HttpResponse<UserExtPulse>) => userExt.body));
        }
        return of(new UserExtPulse());
    }
}

export const userExtRoute: Routes = [
    {
        path: 'user-ext-pulse',
        component: UserExtPulseComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'UserExts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-ext-pulse/:id/view',
        component: UserExtPulseDetailComponent,
        resolve: {
            userExt: UserExtPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserExts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-ext-pulse/new',
        component: UserExtPulseUpdateComponent,
        resolve: {
            userExt: UserExtPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserExts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-ext-pulse/:id/edit',
        component: UserExtPulseUpdateComponent,
        resolve: {
            userExt: UserExtPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserExts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userExtPopupRoute: Routes = [
    {
        path: 'user-ext-pulse/:id/delete',
        component: UserExtPulseDeletePopupComponent,
        resolve: {
            userExt: UserExtPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserExts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
