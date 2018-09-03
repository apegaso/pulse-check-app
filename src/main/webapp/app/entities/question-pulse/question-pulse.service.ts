import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IQuestionPulse } from 'app/shared/model/question-pulse.model';

type EntityResponseType = HttpResponse<IQuestionPulse>;
type EntityArrayResponseType = HttpResponse<IQuestionPulse[]>;

@Injectable({ providedIn: 'root' })
export class QuestionPulseService {
    private resourceUrl = SERVER_API_URL + 'api/questions';

    constructor(private http: HttpClient) {}

    create(question: IQuestionPulse): Observable<EntityResponseType> {
        return this.http.post<IQuestionPulse>(this.resourceUrl, question, { observe: 'response' });
    }

    update(question: IQuestionPulse): Observable<EntityResponseType> {
        return this.http.put<IQuestionPulse>(this.resourceUrl, question, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IQuestionPulse>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IQuestionPulse[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
