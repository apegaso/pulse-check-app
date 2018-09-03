import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ParticipantPulse } from 'app/shared/model/participant-pulse.model';
import { ParticipantPulseService } from './participant-pulse.service';
import { ParticipantPulseComponent } from './participant-pulse.component';
import { ParticipantPulseDetailComponent } from './participant-pulse-detail.component';
import { ParticipantPulseUpdateComponent } from './participant-pulse-update.component';
import { ParticipantPulseDeletePopupComponent } from './participant-pulse-delete-dialog.component';
import { IParticipantPulse } from 'app/shared/model/participant-pulse.model';

@Injectable({ providedIn: 'root' })
export class ParticipantPulseResolve implements Resolve<IParticipantPulse> {
    constructor(private service: ParticipantPulseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((participant: HttpResponse<ParticipantPulse>) => participant.body));
        }
        return of(new ParticipantPulse());
    }
}

export const participantRoute: Routes = [
    {
        path: 'participant-pulse',
        component: ParticipantPulseComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Participants'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'participant-pulse/:id/view',
        component: ParticipantPulseDetailComponent,
        resolve: {
            participant: ParticipantPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Participants'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'participant-pulse/new',
        component: ParticipantPulseUpdateComponent,
        resolve: {
            participant: ParticipantPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Participants'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'participant-pulse/:id/edit',
        component: ParticipantPulseUpdateComponent,
        resolve: {
            participant: ParticipantPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Participants'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const participantPopupRoute: Routes = [
    {
        path: 'participant-pulse/:id/delete',
        component: ParticipantPulseDeletePopupComponent,
        resolve: {
            participant: ParticipantPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Participants'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
