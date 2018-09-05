import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IEmail } from 'app/shared/model/email.model';
import { EmailService } from './email.service';

@Component({
    selector: 'jhi-email-update',
    templateUrl: './email-update.component.html'
})
export class EmailUpdateComponent implements OnInit {
    private _email: IEmail;
    isSaving: boolean;
    dateInsert: string;
    dateSent: string;

    constructor(private emailService: EmailService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ email }) => {
            this.email = email;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.email.dateInsert = moment(this.dateInsert, DATE_TIME_FORMAT);
        this.email.dateSent = moment(this.dateSent, DATE_TIME_FORMAT);
        if (this.email.id !== undefined) {
            this.subscribeToSaveResponse(this.emailService.update(this.email));
        } else {
            this.subscribeToSaveResponse(this.emailService.create(this.email));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEmail>>) {
        result.subscribe((res: HttpResponse<IEmail>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get email() {
        return this._email;
    }

    set email(email: IEmail) {
        this._email = email;
        this.dateInsert = moment(email.dateInsert).format(DATE_TIME_FORMAT);
        this.dateSent = moment(email.dateSent).format(DATE_TIME_FORMAT);
    }
}
