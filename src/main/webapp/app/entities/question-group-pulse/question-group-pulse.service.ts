import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IQuestionGroupPulse } from 'app/shared/model/question-group-pulse.model';

type EntityResponseType = HttpResponse<IQuestionGroupPulse>;
type EntityArrayResponseType = HttpResponse<IQuestionGroupPulse[]>;

@Injectable({ providedIn: 'root' })
export class QuestionGroupPulseService {
    private resourceUrl = SERVER_API_URL + 'api/question-groups';

    constructor(private http: HttpClient) {}

    create(questionGroup: IQuestionGroupPulse): Observable<EntityResponseType> {
        return this.http.post<IQuestionGroupPulse>(this.resourceUrl, questionGroup, { observe: 'response' });
    }

    update(questionGroup: IQuestionGroupPulse): Observable<EntityResponseType> {
        return this.http.put<IQuestionGroupPulse>(this.resourceUrl, questionGroup, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IQuestionGroupPulse>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IQuestionGroupPulse[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
