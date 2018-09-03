import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { EventPulse } from 'app/shared/model/event-pulse.model';
import { EventPulseService } from './event-pulse.service';
import { EventPulseComponent } from './event-pulse.component';
import { EventPulseDetailComponent } from './event-pulse-detail.component';
import { EventPulseUpdateComponent } from './event-pulse-update.component';
import { EventPulseDeletePopupComponent } from './event-pulse-delete-dialog.component';
import { IEventPulse } from 'app/shared/model/event-pulse.model';

@Injectable({ providedIn: 'root' })
export class EventPulseResolve implements Resolve<IEventPulse> {
    constructor(private service: EventPulseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((event: HttpResponse<EventPulse>) => event.body));
        }
        return of(new EventPulse());
    }
}

export const eventRoute: Routes = [
    {
        path: 'event-pulse',
        component: EventPulseComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Events'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'event-pulse/:id/view',
        component: EventPulseDetailComponent,
        resolve: {
            event: EventPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Events'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'event-pulse/new',
        component: EventPulseUpdateComponent,
        resolve: {
            event: EventPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Events'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'event-pulse/:id/edit',
        component: EventPulseUpdateComponent,
        resolve: {
            event: EventPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Events'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const eventPopupRoute: Routes = [
    {
        path: 'event-pulse/:id/delete',
        component: EventPulseDeletePopupComponent,
        resolve: {
            event: EventPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Events'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
