import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { OrganizationPulse } from 'app/shared/model/organization-pulse.model';
import { OrganizationPulseService } from './organization-pulse.service';
import { OrganizationPulseComponent } from './organization-pulse.component';
import { OrganizationPulseDetailComponent } from './organization-pulse-detail.component';
import { OrganizationPulseUpdateComponent } from './organization-pulse-update.component';
import { OrganizationPulseDeletePopupComponent } from './organization-pulse-delete-dialog.component';
import { IOrganizationPulse } from 'app/shared/model/organization-pulse.model';

@Injectable({ providedIn: 'root' })
export class OrganizationPulseResolve implements Resolve<IOrganizationPulse> {
    constructor(private service: OrganizationPulseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((organization: HttpResponse<OrganizationPulse>) => organization.body));
        }
        return of(new OrganizationPulse());
    }
}

export const organizationRoute: Routes = [
    {
        path: 'organization-pulse',
        component: OrganizationPulseComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Organizations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'organization-pulse/:id/view',
        component: OrganizationPulseDetailComponent,
        resolve: {
            organization: OrganizationPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Organizations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'organization-pulse/new',
        component: OrganizationPulseUpdateComponent,
        resolve: {
            organization: OrganizationPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Organizations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'organization-pulse/:id/edit',
        component: OrganizationPulseUpdateComponent,
        resolve: {
            organization: OrganizationPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Organizations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const organizationPopupRoute: Routes = [
    {
        path: 'organization-pulse/:id/delete',
        component: OrganizationPulseDeletePopupComponent,
        resolve: {
            organization: OrganizationPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Organizations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
