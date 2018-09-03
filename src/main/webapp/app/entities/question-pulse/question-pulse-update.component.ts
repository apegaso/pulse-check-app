import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IQuestionPulse } from 'app/shared/model/question-pulse.model';
import { QuestionPulseService } from './question-pulse.service';

@Component({
    selector: 'jhi-question-pulse-update',
    templateUrl: './question-pulse-update.component.html'
})
export class QuestionPulseUpdateComponent implements OnInit {
    private _question: IQuestionPulse;
    isSaving: boolean;

    constructor(private questionService: QuestionPulseService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ question }) => {
            this.question = question;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.question.id !== undefined) {
            this.subscribeToSaveResponse(this.questionService.update(this.question));
        } else {
            this.subscribeToSaveResponse(this.questionService.create(this.question));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionPulse>>) {
        result.subscribe((res: HttpResponse<IQuestionPulse>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get question() {
        return this._question;
    }

    set question(question: IQuestionPulse) {
        this._question = question;
    }
}
