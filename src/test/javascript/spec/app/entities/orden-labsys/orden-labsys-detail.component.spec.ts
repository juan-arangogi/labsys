/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LabsysTestModule } from '../../../test.module';
import { OrdenLabsysDetailComponent } from 'app/entities/orden-labsys/orden-labsys-detail.component';
import { OrdenLabsys } from 'app/shared/model/orden-labsys.model';

describe('Component Tests', () => {
    describe('OrdenLabsys Management Detail Component', () => {
        let comp: OrdenLabsysDetailComponent;
        let fixture: ComponentFixture<OrdenLabsysDetailComponent>;
        const route = ({ data: of({ orden: new OrdenLabsys(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LabsysTestModule],
                declarations: [OrdenLabsysDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(OrdenLabsysDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OrdenLabsysDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.orden).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
