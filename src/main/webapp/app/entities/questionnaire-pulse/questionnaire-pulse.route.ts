import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { QuestionnairePulse } from 'app/shared/model/questionnaire-pulse.model';
import { QuestionnairePulseService } from './questionnaire-pulse.service';
import { QuestionnairePulseComponent } from './questionnaire-pulse.component';
import { QuestionnairePulseDetailComponent } from './questionnaire-pulse-detail.component';
import { QuestionnairePulseUpdateComponent } from './questionnaire-pulse-update.component';
import { QuestionnairePulseDeletePopupComponent } from './questionnaire-pulse-delete-dialog.component';
import { IQuestionnairePulse } from 'app/shared/model/questionnaire-pulse.model';

@Injectable({ providedIn: 'root' })
export class QuestionnairePulseResolve implements Resolve<IQuestionnairePulse> {
    constructor(private service: QuestionnairePulseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((questionnaire: HttpResponse<QuestionnairePulse>) => questionnaire.body));
        }
        return of(new QuestionnairePulse());
    }
}

export const questionnaireRoute: Routes = [
    {
        path: 'questionnaire-pulse',
        component: QuestionnairePulseComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Questionnaires'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'questionnaire-pulse/:id/view',
        component: QuestionnairePulseDetailComponent,
        resolve: {
            questionnaire: QuestionnairePulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Questionnaires'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'questionnaire-pulse/new',
        component: QuestionnairePulseUpdateComponent,
        resolve: {
            questionnaire: QuestionnairePulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Questionnaires'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'questionnaire-pulse/:id/edit',
        component: QuestionnairePulseUpdateComponent,
        resolve: {
            questionnaire: QuestionnairePulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Questionnaires'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const questionnairePopupRoute: Routes = [
    {
        path: 'questionnaire-pulse/:id/delete',
        component: QuestionnairePulseDeletePopupComponent,
        resolve: {
            questionnaire: QuestionnairePulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Questionnaires'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
