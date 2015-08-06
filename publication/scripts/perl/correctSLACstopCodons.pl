open(IN, $ARGV[0]) or die "can't open\n";
@stopPositions = ("14","15","20","182","334","443","478","537","602","609","655","669","673","682","712","725","729","737","739","751","869","897","915");
@data;

while(<IN>){
	chomp($line = $_);
	push(@data, $line);
}
close(IN);

print "read data - ".scalar(@data)." sites with information\n";


open(OUT, '>', $ARGV[0]."resultsCorrected.csv") or die "can't open outfile\n";

for($i = 1; $i<909;$i++){
	$stopHere = 0;
	foreach $position(@stopPositions){
		if($i==$position){
			$stopHere = 1;
		}
	}
	if($stopHere>0){
		print OUT "$i,na,na,na,na,na,na,na\r";
	}else{
		$dNdS_data = shift(@data);
		print OUT "$i,$dNdS_data\r";
	}
}

close(OUT);
