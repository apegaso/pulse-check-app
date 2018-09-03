import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { QuestionPulse } from 'app/shared/model/question-pulse.model';
import { QuestionPulseService } from './question-pulse.service';
import { QuestionPulseComponent } from './question-pulse.component';
import { QuestionPulseDetailComponent } from './question-pulse-detail.component';
import { QuestionPulseUpdateComponent } from './question-pulse-update.component';
import { QuestionPulseDeletePopupComponent } from './question-pulse-delete-dialog.component';
import { IQuestionPulse } from 'app/shared/model/question-pulse.model';

@Injectable({ providedIn: 'root' })
export class QuestionPulseResolve implements Resolve<IQuestionPulse> {
    constructor(private service: QuestionPulseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((question: HttpResponse<QuestionPulse>) => question.body));
        }
        return of(new QuestionPulse());
    }
}

export const questionRoute: Routes = [
    {
        path: 'question-pulse',
        component: QuestionPulseComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Questions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'question-pulse/:id/view',
        component: QuestionPulseDetailComponent,
        resolve: {
            question: QuestionPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Questions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'question-pulse/new',
        component: QuestionPulseUpdateComponent,
        resolve: {
            question: QuestionPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Questions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'question-pulse/:id/edit',
        component: QuestionPulseUpdateComponent,
        resolve: {
            question: QuestionPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Questions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const questionPopupRoute: Routes = [
    {
        path: 'question-pulse/:id/delete',
        component: QuestionPulseDeletePopupComponent,
        resolve: {
            question: QuestionPulseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Questions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
