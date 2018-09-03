/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PulseCheckAppTestModule } from '../../../test.module';
import { QuestionPulseDetailComponent } from 'app/entities/question-pulse/question-pulse-detail.component';
import { QuestionPulse } from 'app/shared/model/question-pulse.model';

describe('Component Tests', () => {
    describe('QuestionPulse Management Detail Component', () => {
        let comp: QuestionPulseDetailComponent;
        let fixture: ComponentFixture<QuestionPulseDetailComponent>;
        const route = ({ data: of({ question: new QuestionPulse(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [QuestionPulseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(QuestionPulseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QuestionPulseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.question).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
