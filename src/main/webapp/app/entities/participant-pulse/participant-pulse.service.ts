import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IParticipantPulse } from 'app/shared/model/participant-pulse.model';

type EntityResponseType = HttpResponse<IParticipantPulse>;
type EntityArrayResponseType = HttpResponse<IParticipantPulse[]>;

@Injectable({ providedIn: 'root' })
export class ParticipantPulseService {
    private resourceUrl = SERVER_API_URL + 'api/participants';

    constructor(private http: HttpClient) {}

    create(participant: IParticipantPulse): Observable<EntityResponseType> {
        return this.http.post<IParticipantPulse>(this.resourceUrl, participant, { observe: 'response' });
    }

    update(participant: IParticipantPulse): Observable<EntityResponseType> {
        return this.http.put<IParticipantPulse>(this.resourceUrl, participant, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IParticipantPulse>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IParticipantPulse[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
