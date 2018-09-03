/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PulseCheckAppTestModule } from '../../../test.module';
import { QuestionnairePulseComponent } from 'app/entities/questionnaire-pulse/questionnaire-pulse.component';
import { QuestionnairePulseService } from 'app/entities/questionnaire-pulse/questionnaire-pulse.service';
import { QuestionnairePulse } from 'app/shared/model/questionnaire-pulse.model';

describe('Component Tests', () => {
    describe('QuestionnairePulse Management Component', () => {
        let comp: QuestionnairePulseComponent;
        let fixture: ComponentFixture<QuestionnairePulseComponent>;
        let service: QuestionnairePulseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [QuestionnairePulseComponent],
                providers: []
            })
                .overrideTemplate(QuestionnairePulseComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(QuestionnairePulseComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuestionnairePulseService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new QuestionnairePulse(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.questionnaires[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
