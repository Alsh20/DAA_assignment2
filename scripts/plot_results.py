import csv, math
from pathlib import Path
from PIL import Image, ImageDraw, ImageFont
root=Path(__file__).resolve().parents[1]
with (root/'results/results.csv').open(newline='',encoding='utf-8') as f: rows=list(csv.DictReader(f))
base={'DynamicArray':(30,100,190),'MyLinkedList':(220,90,45),'MinHeap':(40,145,90)}
font=ImageFont.load_default()
def draw_chart(workload):
 data=[r for r in rows if r['workload']==workload]; W,H=1200,880
 im=Image.new('RGB',(W,H),'white'); d=ImageDraw.Draw(im)
 d.text((45,18),f'{workload}: median benchmark results (log scale)',fill='black',font=font)
 metrics=[('time_ms','Median time (ms)'),('steps','Steps'),('moves','Moves'),('comparisons','Comparisons')]
 variants=sorted(set(r['variant'] for r in data))
 for pi,(metric,title) in enumerate(metrics):
  top=65+pi*200; x0,y0,x1,y1=100,top+20,1135,top+150
  d.text((45,top+20),title,fill='black',font=font); d.line((x0,y0,x0,y1,x1,y1),fill='black',width=2)
  series=[]
  for st in sorted(set(r['structure'] for r in data)):
   for variant in variants:
    rr=sorted([r for r in data if r['structure']==st and r['variant']==variant],key=lambda r:int(r['n']))
    if not rr: continue
    label=st+(f' {variant}' if workload=='W3' else '')
    color=base[st]; series.append((label,color,[(int(r['n']),float(r[metric])) for r in rr]))
  vals=[v for _,_,pts in series for _,v in pts if v>0]
  lo=math.floor(math.log10(min(vals))) if vals else 0; hi=math.ceil(math.log10(max(vals))) if vals else 1
  if hi==lo: hi+=1
  if vals and max(vals)==0: d.text((x0+250,(y0+y1)//2),'0 comparisons recorded for this workload',fill='black',font=font); series=[]
  for k in range(lo,hi+1):
   y=y1-int((y1-y0)*(k-lo)/(hi-lo));d.line((x0-4,y,x1,y),fill=(225,225,225));d.text((55,y-5),f'1e{k}',fill='black',font=font)
  for idx,(label,color,pts) in enumerate(series):
   coords=[]
   for n,v in pts:
    if v<=0:continue
    x=x0+int((x1-x0)*(math.log10(n)-2)/3);y=y1-int((y1-y0)*(math.log10(v)-lo)/(hi-lo));coords.append((x,y))
   for a,b in zip(coords,coords[1:]):
    if workload=='W3' and label.endswith('middle'):
     dx=b[0]-a[0];dy=b[1]-a[1]
     for q in range(0,10,2):d.line((a[0]+dx*q/10,a[1]+dy*q/10,a[0]+dx*(q+1)/10,a[1]+dy*(q+1)/10),fill=color,width=3)
    else:d.line((a,b),fill=color,width=3)
   for x,y in coords:d.ellipse((x-4,y-4,x+4,y+4),fill=color)
   lx=115 if idx<4 else 440; ly=top+2+(idx%4)*12
   d.line((lx,ly+4,lx+20,ly+4),fill=color,width=3);d.text((lx+25,ly),label,fill=color,font=font)
  for j,n in enumerate((100,1000,10000,100000)):
   x=x0+int((x1-x0)*j/3);d.line((x,y1,x,y1+4),fill='black');d.text((x-15,y1+7),str(n),fill='black',font=font)
  d.text((570,y1+7),'n (elements)',fill='black',font=font)
 im.save(root/'results'/'plots'/f'{workload.lower()}_metrics.png')
for w in ('W1','W2','W3','W4'):draw_chart(w)

