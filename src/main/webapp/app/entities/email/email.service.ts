import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEmail } from 'app/shared/model/email.model';

type EntityResponseType = HttpResponse<IEmail>;
type EntityArrayResponseType = HttpResponse<IEmail[]>;

@Injectable({ providedIn: 'root' })
export class EmailService {
    private resourceUrl = SERVER_API_URL + 'api/emails';

    constructor(private http: HttpClient) {}

    create(email: IEmail): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(email);
        return this.http
            .post<IEmail>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(email: IEmail): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(email);
        return this.http
            .put<IEmail>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IEmail>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IEmail[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(email: IEmail): IEmail {
        const copy: IEmail = Object.assign({}, email, {
            dateInsert: email.dateInsert != null && email.dateInsert.isValid() ? email.dateInsert.toJSON() : null,
            dateSent: email.dateSent != null && email.dateSent.isValid() ? email.dateSent.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dateInsert = res.body.dateInsert != null ? moment(res.body.dateInsert) : null;
        res.body.dateSent = res.body.dateSent != null ? moment(res.body.dateSent) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((email: IEmail) => {
            email.dateInsert = email.dateInsert != null ? moment(email.dateInsert) : null;
            email.dateSent = email.dateSent != null ? moment(email.dateSent) : null;
        });
        return res;
    }
}
