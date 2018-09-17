import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { QuestionGroupPulse } from 'app/shared/model/question-group-pulse.model';
import { QuestionGroupPulseService } from './question-group-pulse.service';
import { QuestionGroupPulseComponent } from './question-group-pulse.component';
import { QuestionGroupPulseDetailComponent } from './question-group-pulse-detail.component';
import { QuestionGroupPulseUpdateComponent } from './question-group-pulse-update.component';
import { QuestionGroupPulseDeletePopupComponent } from './question-group-pulse-delete-dialog.component';
import { IQuestionGroupPulse } from 'app/shared/model/question-group-pulse.model';

@Injectable({ providedIn: 'root' })
export class QuestionGroupPulseResolve implements Resolve<IQuestionGroupPulse> {
    constructor(private service: QuestionGroupPulseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((questionGroup: HttpResponse<QuestionGroupPulse>) => questionGroup.body));
        }
        return of(new QuestionGroupPulse());
    }
}

export const questionGroupRoute: Routes = [
    {
        path: 'question-group-pulse',
        component: QuestionGroupPulseComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuestionGroups'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'question-group-pulse/:id/view',
        component: QuestionGroupPulseDetailComponent,
        resolve: {
            questionGroup: QuestionGroupPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuestionGroups'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'question-group-pulse/new',
        component: QuestionGroupPulseUpdateComponent,
        resolve: {
            questionGroup: QuestionGroupPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuestionGroups'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'question-group-pulse/:id/edit',
        component: QuestionGroupPulseUpdateComponent,
        resolve: {
            questionGroup: QuestionGroupPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuestionGroups'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const questionGroupPopupRoute: Routes = [
    {
        path: 'question-group-pulse/:id/delete',
        component: QuestionGroupPulseDeletePopupComponent,
        resolve: {
            questionGroup: QuestionGroupPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuestionGroups'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
