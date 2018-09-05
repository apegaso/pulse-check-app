/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PulseCheckAppTestModule } from '../../../test.module';
import { CategoryLevelPulseDetailComponent } from 'app/entities/category-level-pulse/category-level-pulse-detail.component';
import { CategoryLevelPulse } from 'app/shared/model/category-level-pulse.model';

describe('Component Tests', () => {
    describe('CategoryLevelPulse Management Detail Component', () => {
        let comp: CategoryLevelPulseDetailComponent;
        let fixture: ComponentFixture<CategoryLevelPulseDetailComponent>;
        const route = ({ data: of({ categoryLevel: new CategoryLevelPulse(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [CategoryLevelPulseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CategoryLevelPulseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CategoryLevelPulseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.categoryLevel).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
