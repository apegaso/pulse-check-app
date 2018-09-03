import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { QuestionnaireAnswerPulse } from 'app/shared/model/questionnaire-answer-pulse.model';
import { QuestionnaireAnswerPulseService } from './questionnaire-answer-pulse.service';
import { QuestionnaireAnswerPulseComponent } from './questionnaire-answer-pulse.component';
import { QuestionnaireAnswerPulseDetailComponent } from './questionnaire-answer-pulse-detail.component';
import { QuestionnaireAnswerPulseUpdateComponent } from './questionnaire-answer-pulse-update.component';
import { QuestionnaireAnswerPulseDeletePopupComponent } from './questionnaire-answer-pulse-delete-dialog.component';
import { IQuestionnaireAnswerPulse } from 'app/shared/model/questionnaire-answer-pulse.model';

@Injectable({ providedIn: 'root' })
export class QuestionnaireAnswerPulseResolve implements Resolve<IQuestionnaireAnswerPulse> {
    constructor(private service: QuestionnaireAnswerPulseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service
                .find(id)
                .pipe(map((questionnaireAnswer: HttpResponse<QuestionnaireAnswerPulse>) => questionnaireAnswer.body));
        }
        return of(new QuestionnaireAnswerPulse());
    }
}

export const questionnaireAnswerRoute: Routes = [
    {
        path: 'questionnaire-answer-pulse',
        component: QuestionnaireAnswerPulseComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'QuestionnaireAnswers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'questionnaire-answer-pulse/:id/view',
        component: QuestionnaireAnswerPulseDetailComponent,
        resolve: {
            questionnaireAnswer: QuestionnaireAnswerPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuestionnaireAnswers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'questionnaire-answer-pulse/new',
        component: QuestionnaireAnswerPulseUpdateComponent,
        resolve: {
            questionnaireAnswer: QuestionnaireAnswerPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuestionnaireAnswers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'questionnaire-answer-pulse/:id/edit',
        component: QuestionnaireAnswerPulseUpdateComponent,
        resolve: {
            questionnaireAnswer: QuestionnaireAnswerPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuestionnaireAnswers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const questionnaireAnswerPopupRoute: Routes = [
    {
        path: 'questionnaire-answer-pulse/:id/delete',
        component: QuestionnaireAnswerPulseDeletePopupComponent,
        resolve: {
            questionnaireAnswer: QuestionnaireAnswerPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuestionnaireAnswers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
