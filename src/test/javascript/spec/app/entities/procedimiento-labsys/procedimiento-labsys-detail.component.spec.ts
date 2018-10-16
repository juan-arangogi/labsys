/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LabsysTestModule } from '../../../test.module';
import { ProcedimientoLabsysDetailComponent } from 'app/entities/procedimiento-labsys/procedimiento-labsys-detail.component';
import { ProcedimientoLabsys } from 'app/shared/model/procedimiento-labsys.model';

describe('Component Tests', () => {
    describe('ProcedimientoLabsys Management Detail Component', () => {
        let comp: ProcedimientoLabsysDetailComponent;
        let fixture: ComponentFixture<ProcedimientoLabsysDetailComponent>;
        const route = ({ data: of({ procedimiento: new ProcedimientoLabsys(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LabsysTestModule],
                declarations: [ProcedimientoLabsysDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProcedimientoLabsysDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProcedimientoLabsysDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.procedimiento).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
