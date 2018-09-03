import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ClientLeadPulse } from 'app/shared/model/client-lead-pulse.model';
import { ClientLeadPulseService } from './client-lead-pulse.service';
import { ClientLeadPulseComponent } from './client-lead-pulse.component';
import { ClientLeadPulseDetailComponent } from './client-lead-pulse-detail.component';
import { ClientLeadPulseUpdateComponent } from './client-lead-pulse-update.component';
import { ClientLeadPulseDeletePopupComponent } from './client-lead-pulse-delete-dialog.component';
import { IClientLeadPulse } from 'app/shared/model/client-lead-pulse.model';

@Injectable({ providedIn: 'root' })
export class ClientLeadPulseResolve implements Resolve<IClientLeadPulse> {
    constructor(private service: ClientLeadPulseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((clientLead: HttpResponse<ClientLeadPulse>) => clientLead.body));
        }
        return of(new ClientLeadPulse());
    }
}

export const clientLeadRoute: Routes = [
    {
        path: 'client-lead-pulse',
        component: ClientLeadPulseComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ClientLeads'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'client-lead-pulse/:id/view',
        component: ClientLeadPulseDetailComponent,
        resolve: {
            clientLead: ClientLeadPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClientLeads'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'client-lead-pulse/new',
        component: ClientLeadPulseUpdateComponent,
        resolve: {
            clientLead: ClientLeadPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClientLeads'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'client-lead-pulse/:id/edit',
        component: ClientLeadPulseUpdateComponent,
        resolve: {
            clientLead: ClientLeadPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClientLeads'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clientLeadPopupRoute: Routes = [
    {
        path: 'client-lead-pulse/:id/delete',
        component: ClientLeadPulseDeletePopupComponent,
        resolve: {
            clientLead: ClientLeadPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClientLeads'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
