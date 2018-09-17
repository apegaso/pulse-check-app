import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IQuestionGroupPulse } from 'app/shared/model/question-group-pulse.model';
import { QuestionGroupPulseService } from './question-group-pulse.service';

@Component({
    selector: 'jhi-question-group-pulse-update',
    templateUrl: './question-group-pulse-update.component.html'
})
export class QuestionGroupPulseUpdateComponent implements OnInit {
    private _questionGroup: IQuestionGroupPulse;
    isSaving: boolean;

    constructor(private questionGroupService: QuestionGroupPulseService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ questionGroup }) => {
            this.questionGroup = questionGroup;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.questionGroup.id !== undefined) {
            this.subscribeToSaveResponse(this.questionGroupService.update(this.questionGroup));
        } else {
            this.subscribeToSaveResponse(this.questionGroupService.create(this.questionGroup));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionGroupPulse>>) {
        result.subscribe((res: HttpResponse<IQuestionGroupPulse>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get questionGroup() {
        return this._questionGroup;
    }

    set questionGroup(questionGroup: IQuestionGroupPulse) {
        this._questionGroup = questionGroup;
    }
}
