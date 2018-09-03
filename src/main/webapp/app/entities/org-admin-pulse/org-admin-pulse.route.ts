import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { OrgAdminPulse } from 'app/shared/model/org-admin-pulse.model';
import { OrgAdminPulseService } from './org-admin-pulse.service';
import { OrgAdminPulseComponent } from './org-admin-pulse.component';
import { OrgAdminPulseDetailComponent } from './org-admin-pulse-detail.component';
import { OrgAdminPulseUpdateComponent } from './org-admin-pulse-update.component';
import { OrgAdminPulseDeletePopupComponent } from './org-admin-pulse-delete-dialog.component';
import { IOrgAdminPulse } from 'app/shared/model/org-admin-pulse.model';

@Injectable({ providedIn: 'root' })
export class OrgAdminPulseResolve implements Resolve<IOrgAdminPulse> {
    constructor(private service: OrgAdminPulseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((orgAdmin: HttpResponse<OrgAdminPulse>) => orgAdmin.body));
        }
        return of(new OrgAdminPulse());
    }
}

export const orgAdminRoute: Routes = [
    {
        path: 'org-admin-pulse',
        component: OrgAdminPulseComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'OrgAdmins'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'org-admin-pulse/:id/view',
        component: OrgAdminPulseDetailComponent,
        resolve: {
            orgAdmin: OrgAdminPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrgAdmins'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'org-admin-pulse/new',
        component: OrgAdminPulseUpdateComponent,
        resolve: {
            orgAdmin: OrgAdminPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrgAdmins'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'org-admin-pulse/:id/edit',
        component: OrgAdminPulseUpdateComponent,
        resolve: {
            orgAdmin: OrgAdminPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrgAdmins'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const orgAdminPopupRoute: Routes = [
    {
        path: 'org-admin-pulse/:id/delete',
        component: OrgAdminPulseDeletePopupComponent,
        resolve: {
            orgAdmin: OrgAdminPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrgAdmins'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
