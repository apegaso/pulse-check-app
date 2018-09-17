/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PulseCheckAppTestModule } from '../../../test.module';
import { QuestionGroupPulseDetailComponent } from 'app/entities/question-group-pulse/question-group-pulse-detail.component';
import { QuestionGroupPulse } from 'app/shared/model/question-group-pulse.model';

describe('Component Tests', () => {
    describe('QuestionGroupPulse Management Detail Component', () => {
        let comp: QuestionGroupPulseDetailComponent;
        let fixture: ComponentFixture<QuestionGroupPulseDetailComponent>;
        const route = ({ data: of({ questionGroup: new QuestionGroupPulse(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [QuestionGroupPulseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(QuestionGroupPulseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QuestionGroupPulseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.questionGroup).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
