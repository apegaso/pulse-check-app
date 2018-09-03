/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PulseCheckAppTestModule } from '../../../test.module';
import { QuestionnairePulseDetailComponent } from 'app/entities/questionnaire-pulse/questionnaire-pulse-detail.component';
import { QuestionnairePulse } from 'app/shared/model/questionnaire-pulse.model';

describe('Component Tests', () => {
    describe('QuestionnairePulse Management Detail Component', () => {
        let comp: QuestionnairePulseDetailComponent;
        let fixture: ComponentFixture<QuestionnairePulseDetailComponent>;
        const route = ({ data: of({ questionnaire: new QuestionnairePulse(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [QuestionnairePulseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(QuestionnairePulseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QuestionnairePulseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.questionnaire).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
